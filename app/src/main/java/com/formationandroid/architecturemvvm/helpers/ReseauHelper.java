package com.formationandroid.architecturemvvm.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ReseauHelper
{
	
	/**
	 * Retourne true si connecté à internet.
	 * @param context Context
	 * @return Booléen
	 */
	public static boolean estConnecte(Context context)
	{
		// récupération du manager :
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null)
		{
			// récupération de l'état de la connexion :
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null)
			{
				return networkInfo.isConnected();
			}
		}
		return false;
	}
	
}
