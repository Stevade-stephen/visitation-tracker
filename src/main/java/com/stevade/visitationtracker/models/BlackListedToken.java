package com.stevade.visitationtracker.models;

import com.stevade.visitationtracker.common.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlackListedToken extends Base {
    private String token;
}
