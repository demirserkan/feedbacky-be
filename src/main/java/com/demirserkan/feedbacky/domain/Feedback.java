package com.demirserkan.feedbacky.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Feedbacky Api model documentation", description = "Model")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(value = "Customer id field of feedback object", required = true)
    @NotNull
    private String customerId;

    @ApiModelProperty(value = "Feedback message field of feedback object", required = true)
    @NotNull
    @Size(max = 2000)
    private String message;

    @ApiModelProperty(hidden = true)
    private LocalDateTime insertDate;
}
