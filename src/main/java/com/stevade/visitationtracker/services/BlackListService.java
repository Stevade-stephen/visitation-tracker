package com.stevade.visitationtracker.services;

import com.stevade.visitationtracker.models.BlackListedToken;

public interface BlackListService {

    BlackListedToken blackListToken(String  token);

    BlackListedToken getToken(String  token);
}
