package com.example.continent.domain.config_;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.continent.application.dto_.UserDto;
import com.example.continent.application.exception_.BaseException;
import com.example.continent.application.mapper_.UserMapper;
import com.example.continent.domain.model_.Role;
import com.example.continent.domain.model_.User;
import com.example.continent.domain.service.RoleService;
import com.example.continent.domain.service.UserService;

public class A_Todo {
    /**
     * Viết cái này  ra (SimpleAuditorAware) nhưng không dùng.
     * 
     * Trong dự án hiện tại chưa thấy có yêu cầu người dùng login thành công
     *   xong phải thao tác gì đó để xác minh ai đang login để làm gì nên chắc có cơ hội sử dụng.
     * Ví dụ method sử dụng :
     * Trường hợp giả định : nếu người dùng đang login có thể phân quyền cho ông A từ role manager hoặc là admin nhưng hạ cấp ổng xuống thành view thì mình sử dụng nó như sau.
     */

    // @Service 
    public class ExampleService{

        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_VIEW = "ROLE_VIEW";

        @Autowired
        SimpleAuditorAware simpleAuditorAware;

        @Autowired
        UserService userService;

        @Autowired
        UserMapper userMapper;

        @Autowired
        RoleService roleService;

        public void changeRole(Long userIdNeedChange) {
        String username = simpleAuditorAware.getCurrentAuditor()
            .orElseThrow(() -> new UsernameNotFoundException("Current auditor not found")); // <- Dùng ở đấy.
        
            User currentUser = userService.getByUsername(username);  
            if(!currentUserIsSuperAdmin(currentUser)) {
                throw new UserNotAllowException(HttpStatus.BAD_GATEWAY,"Current user isn't Admin Role !!!");
            }
            UserDto changedDto = userService.getById(userIdNeedChange);
            changeRoleToView(changedDto);
    }
    
    private boolean currentUserIsSuperAdmin(User currentUser){
        return  currentUser.getRoles().stream()
            .anyMatch(role -> ROLE_ADMIN.equals(role.getName()));
    }

    private void changeRoleToView(UserDto dto){
        User change = userMapper.toEntity(dto);
        change.getRoles().clear();
        Role role = roleService.getRoleByName(ROLE_VIEW);
        change.setRoles(new ArrayList<>(java.util.Arrays.asList(role)));
        userService.save(change);
        // xóa các mối quan hệ users_roles liên quan ... 
    }

}
    public class UserNotAllowException extends BaseException {

        protected UserNotAllowException(HttpStatus status, String code, Object[] args) {
            super(status, code, args);
        }

        protected UserNotAllowException(HttpStatus status, String code){
            super(status, code);
        }

    }
}