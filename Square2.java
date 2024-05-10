import java.awt.*;

public class Square
{
    int value;

    Color squareColor;
    private boolean merged;
    private int animationDX = 0;
    private int animationDY = 0;


    public Square()
    {
        value = 0;

        this.merged = false;
    }


    public Square(int number)
    {
        value = number;
    }


    public int getValue()
    {
        return value;
    }

    public void setValue( int value )
    {
        this.value = value;
    }

    public String toString()
    {
        return "" + value;
    }


    public void setColor()
    {
        if ( this.getValue() == 2 )
        {
            squareColor = new Color( 238, 228, 218 );
        }
        else if ( this.getValue() == 4 )
        {
            squareColor = new Color( 237, 224, 200 );
        }
        else if ( this.getValue() == 8 )
        {
            squareColor = new Color( 242, 177, 121 );
        }
        else if ( this.getValue() == 16 )
        {
            squareColor = new Color( 245, 149, 99 );
        }
        else if ( this.getValue() == 32 )
        {
            squareColor = new Color( 246, 124, 95 );
        }
        else if ( this.getValue() == 64 )
        {
            squareColor = new Color( 246, 94, 59 );
        }
        else if ( this.getValue() == 128 )
        {
            squareColor = new Color( 237, 207, 114 );
        }
        else if ( this.getValue() == 256 )
        {
            squareColor = new Color( 237, 204, 97 );
        }
        else if ( this.getValue() == 512 )
        {
            squareColor = new Color( 237, 200, 80 );
        }
        else if ( this.getValue() == 1024 )
        {
            squareColor = new Color( 237, 197, 63 );
        }
        else
        {
            squareColor = new Color( 237, 194, 46 );
        }
    }

    public Color getColor()
    {
        this.setColor();
        return squareColor;
    }
    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }
    public int getAnimationDX() {
        return animationDX;
    }

    public void setAnimationDX(int animationDX) {
        this.animationDX = animationDX;
    }

    public int getAnimationDY() {
        return animationDY;
    }

    public void setAnimationDY(int animationDY) {
        this.animationDY = animationDY;
    }

}
