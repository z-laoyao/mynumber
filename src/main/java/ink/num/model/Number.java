package ink.num.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Number {
    Integer id;
    String nub;
    String high_1;
    String high_2;
    String high_3;
    String high_4;
    String time;

    public Double getMaxHigh() {
        double max = Double.parseDouble(high_1);
        double var = Double.parseDouble(high_2);
        max = max > var ? max : var;
        var = Double.parseDouble(high_3);
        max = max > var ? max : var;
        var = Double.parseDouble(high_4);
        max = max > var ? max : var;
        return max;
    }


}
