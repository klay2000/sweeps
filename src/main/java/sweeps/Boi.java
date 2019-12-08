package sweeps;

import java.util.Arrays;

public class Boi extends Entity{

    private boolean isDead = false;

    public static final float MOVEMENT_COST = 1;
    public static final float EAT_AMOUNT = 5;
    public static final float EAT_COST = 1;

    public static enum  Direction{
        north,
        south,
        east,
        west
    };

    public Boi(int x, int y, int sectorX, int sectorY, float energy) throws InvalidPositionException {
        super(x, y, sectorX, sectorY, "boi");
        setEnergy(energy);

    }

    public boolean moveBoi(Direction direction) throws InvalidPositionException {

        setEnergy(getEnergy()-MOVEMENT_COST);

        int newX = getXPosition();
        int newY = getYPosition();

        switch (direction){
            case north:
                newY++;
                break;

            case south:
                newY--;
                break;

            case east:
                newX++;
                break;

            case west:
                newX--;
                break;
        }

        if(!getSector().isBlocked(newX, newY)){
            setPosition(newX, newY);
            return true;
        }
        else return false;

    }

    public boolean eat(Direction direction) throws InvalidPositionException {

        int newX = getXPosition();
        int newY = getYPosition();

        switch (direction) {
            case north:
                newY++;
                break;

            case south:
                newY--;
                break;

            case east:
                newX++;
                break;

            case west:
                newX--;
                break;
        }
        Entity opp = getSector().getEntity(newX, newY);
        if (opp != null) {
            if (opp.getEnergy() >= EAT_AMOUNT) {
                opp.setEnergy(opp.getEnergy()-EAT_AMOUNT);
                setEnergy(getEnergy()+EAT_AMOUNT-EAT_COST);
            } else {
                setEnergy(getEnergy()+opp.getEnergy()-EAT_COST);
                opp.setEnergy(0);
            }
            return true;
        }
        return false;
    }

    @Override
    public void setEnergy(float energy) throws InvalidPositionException {
        super.setEnergy(energy);
        if(energy <= 0){
            getSector().entities.remove(this);
            isDead = true;
        }
    }

    public Boolean getIsDead(){
        return isDead;
    }

}
