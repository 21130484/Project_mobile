package model;

import lombok.*;

import java.sql.Timestamp;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public class Rate {
        private String fullName;
        private int starRatings;
        private String comment;
        //    @JsonSerialize(using = JsonDateSerializer.class)
        private Timestamp createDate;
}
