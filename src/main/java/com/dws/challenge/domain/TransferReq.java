package com.dws.challenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

  @NotNull
  @NotEmpty
  @Min(value = 1)
  private final BigDecimal amount;

  @NotNull
  private String fromAccount;

  @NotNull
  private String toAccount;

  
}
