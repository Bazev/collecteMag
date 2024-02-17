package com.bazin.test.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class JobInfo {

    int countClients;
    int countComptes;
    int countError;
}
