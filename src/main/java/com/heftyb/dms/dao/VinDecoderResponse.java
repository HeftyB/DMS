package com.heftyb.dms.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VinDecoderResponse(int Count, String Message, String SearchCriteria, HashMap<String, String>[] Results) {
}
