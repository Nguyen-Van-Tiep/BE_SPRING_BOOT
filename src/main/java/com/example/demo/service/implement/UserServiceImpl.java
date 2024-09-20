package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Email;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.enums.ErrorCode;
import com.example.demo.model.DTO.CustomerDto;
import com.example.demo.model.DTO.EmployeeDTO;
import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.info.UserInfo;
import com.example.demo.model.request.*;
import com.example.demo.model.response.GetCustomerResponse;
import com.example.demo.model.response.GetEmployeeResponse;
import com.example.demo.model.result.CustomerResult;
import com.example.demo.model.result.EmployeeResult;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.model.utilities.Constant;
import com.example.demo.model.utilities.SercurityUtils;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    private PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    public UserServiceImpl(@Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<User> customers = userRepository.findAllByRolesName("USER_ROLE")
                .stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> "USER_ROLE".equals(role.getName())))
                .collect(Collectors.toList());

        return customers.stream()
                .map(CommonUtil::toCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<User> employees = userRepository.findAllByRolesName("ADMIN_ROLE")
                .stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> "ADMIN_ROLE".equals(role.getName())))
                .collect(Collectors.toList());
        return employees.stream()
                .map(CommonUtil::toEmployeeDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public User getUserById(Long userId) throws BusinessException {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    @Override
    public String createCustomer(SaveUserRequest request) throws BusinessException {
        String username = request.getUsername();
        String password = request.getPassword();
        // Kiểm tra tồn tại username và số điện thoại trong cơ sở dữ liệu
        if (userRepository.existsByUsername(username)) {
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS,"Tài khoản đã tồn tại");
        }
        if (userRepository.existsByNumberPhone(request.getNumberPhone())) {
            throw new BusinessException(ErrorCode.NUMBER_PHONE_ALREADY_EXISTS,"Số điện thoại đã tồn tại");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS,"Email đã tồn tại");
        }
        User user = new User();
        user.setUsername(username);
        user.setFullName(request.getFullName());
        user.setNumberPhone(request.getNumberPhone());
        user.setEmail(request.getEmail());
        if(request.getDateOfBirth()!=null){
            user.setBirthOfDate(CommonUtil.str2Date(request.getDateOfBirth()));
        }
        user.setGender(request.getGender());
        user.setStatus(CommonUtil.getStatusVn(Constant.STATUS_STYLES.ACTIVE));
        user.setCreatedBy(username);
        user.setModifiedBy(username);
        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        // Lưu người dùng vào cơ sở dữ liệu
        User savedUser = userRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(savedUser.getId().intValue());
        userRole.setRoleId(2);
        userRoleRepository.save(userRole);
        String credentials = username + ":" + password;
        byte[] byteArray = credentials.getBytes();
        String authorication = Base64.getEncoder().encodeToString(byteArray);

        log.info("Created customer: {}", savedUser.getUsername());
        return "Basic " + authorication;
    }

    @Transactional
    @Override
    public String createUserCustomer(CreateUserRequest userRequest) throws BusinessException {
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        String email = userRequest.getEmail();
        String fullname = userRequest.getFullName();

        if (userRepository.existsByUsername(username)) {
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS,"Tài khoản đã tồn tại");
        }
        if (userRepository.existsByNumberPhone(userRequest.getNumberPhone())) {
            throw new BusinessException(ErrorCode.NUMBER_PHONE_ALREADY_EXISTS,"Số điện thoại đã tồn tại");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS,"Email đã tồn tại");
        }
        User user = new User();
        user.setUsername(username);
        user.setFullName(userRequest.getFullName());

        password = CommonUtil.generateRandomString(8);
        user.setPassword(passwordEncoder.encode(password));

        user.setNumberPhone(userRequest.getNumberPhone());
        user.setEmail(userRequest.getEmail());
        if(userRequest.getBirthOfDate()!=null){
            user.setBirthOfDate(userRequest.getBirthOfDate());
        }
        user.setGender(userRequest.getGender());
        user.setStatus(CommonUtil.getStatusVn(Constant.STATUS_STYLES.ACTIVE));
        user.setCreatedBy(username);
        user.setModifiedBy(username);
        User savedUser = userRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(savedUser.getId().intValue());
        userRole.setRoleId(2 );
        userRoleRepository.save(userRole);

        Email email1 = new Email();
        email1.setToEmail(email);
        email1.setSubject("Xin chào bạn đã đến với chúng tôi: ");
        if (CommonUtil.isNullOrEmpty(userRequest.getFullName())) {
            fullname = username;
        }
        email1.setBody("Gửi " + fullname + ",\n\n mật khẩu  của bạn là: " + password + "\n\n Vui lòng đổi mật khẩu sau khi đăng nhập hệ thống.");
        emailService.sendPasswordResetEmail(email1);
        return "User created successfully";
    }

    @Transactional
    @Override
    public String createEmployee(SaveCustomerRequest userRequest) throws BusinessException {
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        // Kiểm tra tồn tại username và số điện thoại trong cơ sở dữ liệu
        if (userRepository.existsByUsername(username)) {
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS,"Tài khoản đã tồn tại");
        }
        if (userRepository.existsByNumberPhone(userRequest.getNumberPhone())) {
            throw new BusinessException(ErrorCode.NUMBER_PHONE_ALREADY_EXISTS,"Số điện thoại đã tồn tại");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS,"Email đã tồn tại");
        }
        User user = new User();
        user.setUsername(username);
        user.setFullName(userRequest.getFullName());
        user.setNumberPhone(userRequest.getNumberPhone());
        user.setEmail(userRequest.getEmail());
        user.setBirthOfDate(userRequest.getBirthOfDate());
        user.setGender(userRequest.getGender());
        user.setStatus(CommonUtil.getStatusVn(Constant.STATUS_STYLES.ACTIVE));
        user.setCreatedBy(username);
        user.setModifiedBy(username);
        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        // Lưu người dùng vào cơ sở dữ liệu
        User savedUser = userRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(savedUser.getId().intValue());
        userRole.setRoleId(1);
        userRoleRepository.save(userRole);
        String credentials = username + ":" + password;
        byte[] byteArray = credentials.getBytes();
        String authorication = Base64.getEncoder().encodeToString(byteArray);

        log.info("Created customer: {}", savedUser.getUsername());
        return "Basic " + authorication;
    }

    @Override
    public GetCustomerResponse getCustomer(CustomerRequest request, int page, int size, String sortField, String sortType) throws BusinessException {
        String name = request.getName();
        if (CommonUtil.isNullOrEmpty(name)) {
            name = "";
        }
        Pageable pageable = null;
        if (CommonUtil.isNullOrEmpty(sortField)) {
            pageable = PageRequest.of(page, size);
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(sortType), sortField);
            pageable = PageRequest.of(page, size, sort);
        }
        GetCustomerResponse response = new GetCustomerResponse();
        Page<User> users = userRepository.searchCustomersByUsernameOrEmail(name, pageable);
        List<CustomerResult> results = new ArrayList<>();
        for (User user : users.getContent()) {
            if (user.getRoles().stream().anyMatch(role -> "USER_ROLE".equals(role.getName()))) {
                CustomerResult result = this.convertResponse(user);
                results.add(result);
            }
        }
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(users.getNumber(), users.getSize(), Math.toIntExact(users.getTotalElements()));
        response.setCustomers(results);
        response.setPagination(paginationInfo);
        return response;
    }

    @Override
    public GetEmployeeResponse getEmployee(EmployeeRequest request, int page, int size, String sortField, String sortType) throws BusinessException {
        String name = request.getName();
        if (CommonUtil.isNullOrEmpty(name)) {
            name = "";
        }
        Pageable pageable = null;
        if (CommonUtil.isNullOrEmpty(sortField)) {
            pageable = PageRequest.of(page, size);
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(sortType), sortField);
            pageable = PageRequest.of(page, size, sort);
        }
        GetEmployeeResponse response = new GetEmployeeResponse();
        Page<User> users = userRepository.searchEmployeesByUsernameOrEmail(name, pageable);
        List<EmployeeResult> results = new ArrayList<>();
        for (User user : users.getContent()) {
            if (user.getRoles().stream().anyMatch(role -> "ADMIN_ROLE".equals(role.getName()))) {
                EmployeeResult result = this.convertResponsei(user);
                results.add(result);
            }
        }
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(users.getNumber(), users.getSize(), Math.toIntExact(users.getTotalElements()));
        response.setEmployees(results);
        response.setPagination(paginationInfo);
        return response;
    }

    @Override
    public void updateStatusUser(Long id, String status) throws BusinessException {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND,"Tài khoản không tồn tại"));
        user.setStatus("Hoạt động".equals(status) ? "Hoạt động" : "Ngừng hoạt động");
        //user.setStatus(Constant.STATUS_STYLES.ACTIVE.equals(status) ? "Constant.STATUS_STYLES.ACTIVE" : "Constant.STATUS_STYLES.INACTIVE");
        userRepository.save(user);
    }

    private EmployeeResult convertResponsei(User user) {
        EmployeeResult response = new EmployeeResult();
        response.setFullName(user.getFullName());
        response.setNumberPhone(user.getNumberPhone());
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setGender(user.getGender());
        if(user.getBirthOfDate()!=null){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDateString = formatter.format(user.getBirthOfDate());
            response.setBirthOfDate(strDateString);
        }
        response.setStatus(user.getStatus());
        // Chuyển đổi danh sách vai trò thành chuỗi tên các vai trò
        String roles = user.getRoles().stream()
                .map(Role::getName) // Giả sử Role có phương thức getName() trả về tên vai trò
                .collect(Collectors.joining(", ")); // Nối các tên vai trò thành một chuỗi, ngăn cách bằng dấu phẩy
        response.setRole(roles);
        return response;
    }

    private CustomerResult convertResponse(User user) {
        CustomerResult response = new CustomerResult();
        response.setFullName(user.getFullName());
        response.setNumberPhone(user.getNumberPhone());
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setGender(user.getGender());
        if(user.getBirthOfDate()!=null){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDateString = formatter.format(user.getBirthOfDate());
            response.setBirthOfDate(strDateString);
        }
        response.setStatus(user.getStatus());
        String roles = user.getRoles().stream()
                .map(Role::getName)  // Giả sử Role có phương thức getName() trả về tên vai trò
                .collect(Collectors.joining(", ")); // Nối các tên vai trò thành một chuỗi, ngăn cách bằng dấu phẩy
        response.setRole(roles);
        return response;
    }

    @Override
    public Optional<UserInfo> findUserByUsername(String username) {
        User user = this.userRepository.findUserByUsername(username);
        if (user != null) {
            UserInfo info = new UserInfo();
            info.setUsername(username);
            info.setId(user.getId());
            info.setPassword(user.getPassword());
            List<String> roles = this.roleRepository.getRolesByUser(user.getId());
            if (!roles.isEmpty()) {
                info.setRole(roles.get(0));
            }
            return Optional.of(info);
        }
        return Optional.empty();
    }

    @Override
    public Boolean resetPassword(ResetPasswordRequest request, String username) throws BusinessException {
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        String confirmPassword = request.getConfirmPassword();
        if (!newPassword.equals(confirmPassword)) {
            throw new BusinessException(ErrorCode.PASSWORD_IS_NOT_MATCH,"Mật khẩu xác nhận phải giống mật khẩu mới");
        }
        if (oldPassword.equals(newPassword)) {
            throw new BusinessException(ErrorCode.THE_NEWPASSWORD_MUST_BE_DIFFERENT_FROM_THE_OLDPASSWORD,"Mật khẩu mới phải khác mật khẩu cũ");
        }
        User oUser = userRepository.findUserByUsername(username);

        if (oUser == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND,"Tài khoản không tồn tại");
        }
        boolean isMatch = passwordEncoder.matches(oldPassword, oUser.getPassword());
        if (!isMatch) {
            throw new BusinessException(ErrorCode.PASSWORD_IS_NOT_MATCH,"Mật khẩu xác nhận phải giống mật khẩu mới");
        }
        this.userRepository.resetPassword(oUser.getId(), passwordEncoder.encode(newPassword));
        return true;
    }

    @Override
    @Transactional
    public Boolean forgotPassword(ForgotPasswordRequest request, String username) throws BusinessException {
        username = request.getUsername();
        String email = request.getEmail();
        User user = userRepository.findUserByUsername(username);
        String fullname = user.getFullName();
        if (user == null || !user.getEmail().equals(email)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND,"Tài khoản không tồn tại");
        }
        String password = CommonUtil.generateRandomString(8);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        Email email1 = new Email();
        email1.setToEmail(email);
        email1.setSubject("Quên mật khẩu ");
        if (CommonUtil.isNullOrEmpty(fullname)) {
            fullname = username;
        }
        email1.setBody("Gửi " + fullname + ",\n\nmật khẩu mới của bạn là: " + password + "\n\nVui lòng đổi mật khẩu sau khi đăng nhập hệ thống.");
        emailService.sendPasswordResetEmail(email1);
        return true;
    }

    public Boolean update(UpdatePersonalInformationManagementRequest request) throws BusinessException {
        User update = userRepository.findUserByUsername(SercurityUtils.getCurrentUser());
        if (update == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND,"Tài khoản không tồn tại");
        }
        if (!Objects.equals(update.getNumberPhone(), request.getNumberPhone()) && userRepository.existsByNumberPhoneAndIdNot(request.getNumberPhone(), update.getId())) {
            throw new BusinessException(ErrorCode.NUMBER_PHONE_ALREADY_EXISTS,"Số điện thoại đã tồn tại");
        }
        if (!Objects.equals(update.getEmail(), request.getEmail()) && userRepository.existsByEmailAndIdNot(request.getEmail(), update.getId())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS,"Email đã tồn tại");
        }
        update.setFullName(request.getFullName());
        update.setGender(request.getGender());
        update.setBirthOfDate(CommonUtil.str2Date(request.getBirthOfDate()));
        update.setNumberPhone(request.getNumberPhone());
        update.setEmail(request.getEmail());
        userRepository.save(update);
        return true;
    }

    @Override
    public Boolean updateUser(Long id, UpdateUserRequest request) throws BusinessException {
        User update = userRepository.findById(id).orElse(null);
        if (update == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND,"Tài khoản không tồn tại");
        }
        if (!Objects.equals(update.getNumberPhone(), request.getNumberPhone()) && userRepository.existsByNumberPhoneAndIdNot(request.getNumberPhone(), update.getId())) {
            throw new BusinessException(ErrorCode.NUMBER_PHONE_ALREADY_EXISTS,"Số điện thoại đã tồn tại");
        }
        if (!Objects.equals(update.getEmail(), request.getEmail()) && userRepository.existsByEmailAndIdNot(request.getEmail(), update.getId())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS,"Email đã tồn tại");
        }
//        if (!update.getNumberPhone().equals(request.getNumberPhone()) &&
//                userRepository.existsByNumberPhoneAndIdNot(request.getNumberPhone(), update.getId())) {
//            throw new BusinessException(ErrorCode.NUMBER_PHONE_ALREADY_EXISTS);
//        }
//        if (!update.getEmail().equals(request.getEmail()) &&
//                userRepository.existsByEmailAndIdNot(request.getEmail(), update.getId())) {
//            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
//        }

        update.setFullName(request.getFullName());
        update.setGender(request.getGender());
        update.setBirthOfDate(request.getBirthOfDate());
        update.setNumberPhone(request.getNumberPhone());
        update.setEmail(request.getEmail());
        userRepository.save(update);
        return true;
    }

    public UserDisplay getUserInfo(String username) throws BusinessException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND,"Tài khoản không tồn tại");
        }
        UserDisplay userDisplay = new UserDisplay();
        userDisplay.setUsername(user.getUsername());
        userDisplay.setFullName(user.getFullName());
        if (user.getBirthOfDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDateString = formatter.format(user.getBirthOfDate());
            userDisplay.setBirthOfDate(strDateString);
        }
        userDisplay.setEmail(user.getEmail());
        userDisplay.setNumberPhone(user.getNumberPhone());
        userDisplay.setGender(user.getGender());
        List<String> list = roleRepository.getRolesByUser(user.getId());
        if (list != null) {
            userDisplay.setRole(CommonUtil.convertRole(list.get(0)));
        }

        return userDisplay;
    }

    @Override
    public List<String> getCustomerStatuses() {
        return Arrays.asList(
                Constant.CUSTOMER_STATUS.ACTIVE,
                Constant.CUSTOMER_STATUS.INACTIVE,
                Constant.CUSTOMER_STATUS.LOCKED
        );
    }
}
