package network;

import java.util.ArrayList;
import entity.*;

public class TokensTest {
    public static void main(String[] args) {
        String entitiesReceived = "player,10,229:player,5,12:obstacle,120,291:player,123,34";
        String[] separatedEntities = entitiesReceived.split(":");
        
        ArrayList<Entity> entities = new ArrayList<>();
        
        for(String entityString : separatedEntities){
            Entity tempEntity = new Entity(entityString.split(",")[0]);
            tempEntity.getFigure().setCenter(Double.parseDouble(entityString.split(",")[1]), Double.parseDouble(entityString.split(",")[2]));
            entities.add(tempEntity);
        }
        
        entities.add(1, entities.remove(entities.size()-1));
        
        for(Entity entity : entities)
            System.out.println(entity);
        
    }
}
