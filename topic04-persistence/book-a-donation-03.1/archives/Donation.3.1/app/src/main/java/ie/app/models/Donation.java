package ie.app.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Donation
{
    public long   id;
    public int    amount;
    public String method;

    private static final String JSON_ID         = "id";
    private static final String JSON_AMOUNT     = "amount";
    private static final String JSON_METHOD     = "method";

    public Donation (int amount, String method)
    {
        this.id = unsignedLong();
        this.amount = amount;
        this.method = method;
    }

    public Donation(JSONObject json) throws JSONException
    {
        id       = json.getLong(JSON_ID);
        amount   = json.getInt(JSON_AMOUNT);
        method   = json.getString(JSON_METHOD);
    }

    public JSONObject toJSON() throws JSONException
    {
        JSONObject json = new JSONObject();
        json.put(JSON_ID       , id);
        json.put(JSON_AMOUNT   , amount);
        json.put(JSON_METHOD   , method);
        return json;
    }

    @Override
    public String toString() {
        return "Donation{" + "id= " + id +
                "amount=$" + amount +
                ", method='" + method + '\'' +
                '}';
    }

    /**
     * Generate a long greater than zero
     * @return Unsigned Long value greater than zero
     */
    private Long unsignedLong() {
        long rndVal = 0;
        do {
            rndVal = new Random().nextLong();
        } while (rndVal <= 0);
        return rndVal;
    }
}
