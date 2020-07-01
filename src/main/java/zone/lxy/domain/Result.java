package zone.lxy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {   //json返回结果的封装
    private boolean status;
    private String msg;
    private Object data;
}
