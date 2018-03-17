package ie.app.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import ie.app.models.Donation;
import ie.app.models.DonationSerializer;

public class DonationApp extends Application
{
    public final int       target       = 10000;
    public int             totalDonated = 0;
    public List <Donation> donations    = new ArrayList<Donation>();

    public DonationSerializer serializer;

    public boolean newDonation(Donation donation)
    {
        boolean targetAchieved = totalDonated > target;
        if (!targetAchieved) {
            donations.add(donation);
            totalDonated += donation.amount;
        }
        else
            Toast.makeText(this, "Target Exceeded!", Toast.LENGTH_SHORT).show();

        return targetAchieved;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("Donate", "Donation App Started");
        serializer = new DonationSerializer(this,"donations.json");
            try {
                donations = serializer.loadDonations();
                Log.v("Donate", "Donation JSON File Created/Loaded");
                if(!donations.isEmpty())
                        totalDonated = serializer.getTotalDonated();
                }
            catch (Exception e) // Catch everything!!
                {
                    Log.v("Donate", "Error loading Donations: "
                            + e.getMessage());
                    donations = new ArrayList<Donation>();
                }
    }
}