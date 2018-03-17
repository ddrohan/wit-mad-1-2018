package ie.app.models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

public class DonationSerializer
{
    private Context mContext;
    private String mFilename;
    private int totalDonated;

    public DonationSerializer(Context c, String f)
    {
        mContext = c;
        mFilename = f;
    }

    public void saveDonations(List<Donation> donations)
            throws JSONException, IOException
    {
        // build an array in JSON
        JSONArray array = new JSONArray();
        for (Donation d : donations)
            array.put(d.toJSON());

        // write the file to disk
        Writer writer = null;
        try
        {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }
        finally
        {
            if (writer != null)
                writer.close();
        }
    }

    public ArrayList<Donation> loadDonations()
            throws IOException, JSONException
    {
        ArrayList<Donation> donations = new ArrayList<Donation>();
        BufferedReader reader = null;
        totalDonated = 0;

        try
        {
            // open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                // line breaks are omitted and irrelevant
                jsonString.append(line);
            }

            Log.v("Donate",jsonString.toString());
            // parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // build the array of donations from JSONObjects
            for (int i = 0; i < array.length(); i++)
            {
                donations.add(new Donation(array.getJSONObject(i)));
                totalDonated += donations.get(i).amount;
            }
        }
        catch (FileNotFoundException e)
        {
            // we will ignore this one, since it happens when we start fresh
        }
        finally
        {
            if (reader != null)
                reader.close();
        }
        return donations;
    }

    public int getTotalDonated()
    {
        return totalDonated;
    }
}
