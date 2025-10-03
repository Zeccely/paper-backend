package fontys.i_hate_paper.mapper;

import fontys.i_hate_paper.DTOs.DeliveryDTO;
import fontys.i_hate_paper.Models.Delivery;

public class DeliveryMapper {

    public static DeliveryDTO toPackageDTO(Delivery delivery){
        return new DeliveryDTO(

        );

    }
    public static Delivery toPackage(DeliveryDTO deliveryDTO){
        return new Delivery(

        );
    }
}
