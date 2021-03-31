package com.leo.rabbithello.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "orderNumber",
        "amount"
})
public class Order implements Serializable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("amount")
    private double amount;

}
