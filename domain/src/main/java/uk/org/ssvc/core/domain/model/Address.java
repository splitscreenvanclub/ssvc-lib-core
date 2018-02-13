package uk.org.ssvc.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Address {

    private final String lineOne;
    private final String lineTwo;
    private final String lineThree;
    private final String lineFour;
    private final String county;
    private final String region;
    private final String postcode;

}
