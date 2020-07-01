package zone.lxy.service;

import zone.lxy.domain.User;

public interface UserService {
    void register(User user);

    User findByName(String name);
}
