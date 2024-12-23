package uit.carbon_shop.model;

import lombok.experimental.FieldNameConstants;


@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum UserRole {

    @FieldNameConstants.Include
    SELLER_OR_BUYER,
    @FieldNameConstants.Include
    MEDIATOR

}
