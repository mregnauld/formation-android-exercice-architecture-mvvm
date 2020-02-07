package com.formationandroid.architecturemvvm.repositories;

import android.content.Context;

import com.formationandroid.architecturemvvm.R;
import com.formationandroid.architecturemvvm.helpers.ReseauHelper;
import com.formationandroid.architecturemvvm.models.Planete;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import androidx.lifecycle.MutableLiveData;
import cz.msebera.android.httpclient.Header;

public class MainRepository
{
	
	/**
	 * Récupération d'une planète via le webservice.
	 * @param context Context
	 * @param liveDataPlanete LiveData planète
	 * @param liveDataChargement LiveData chargement
	 */
	public void getLiveDataPlanete(Context context, final MutableLiveData<Planete> liveDataPlanete, final MutableLiveData<Boolean> liveDataChargement)
	{
		// id de planète, entre 1 et 61 inclus :
		int idPlanete = new Random().nextInt(61) + 1;
		
		// client HTTP :
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(30000);
		
		// paramètres :
		RequestParams requestParams = new RequestParams();
		requestParams.put("format", "json");
		
		// appel :
		if (ReseauHelper.estConnecte(context))
		{
			client.get("https://swapi.co/api/planets/" + idPlanete + "/", requestParams, new AsyncHttpResponseHandler()
			{
				@Override
				public void onSuccess(int statusCode, Header[] headers,
									  byte[] response)
				{
					// retour du webservice :
					String retour = new String(response);
					
					// conversion en un objet Java ayant le même format que le JSON :
					Gson gson = new Gson();
					Planete planete = gson.fromJson(retour, Planete.class);
					
					// retour
					liveDataChargement.setValue(false);
					liveDataPlanete.setValue(planete);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
									  byte[] errorResponse, Throwable e)
				{
					// retour en erreur :
					Planete planete = new Planete();
					planete.erreur = e.getMessage();
					
					// retour
					liveDataChargement.setValue(false);
					liveDataPlanete.setValue(planete);
				}
			});
		}
		else
		{
			// erreur :
			Planete planete = new Planete();
			planete.erreur = context.getString(R.string.main_aucun_reseau);
			
			// retour
			liveDataChargement.setValue(false);
			liveDataPlanete.setValue(planete);
		}
	}
	
}
