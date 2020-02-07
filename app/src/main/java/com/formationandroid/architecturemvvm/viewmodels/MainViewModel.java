package com.formationandroid.architecturemvvm.viewmodels;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.formationandroid.architecturemvvm.R;
import com.formationandroid.architecturemvvm.models.Planete;
import com.formationandroid.architecturemvvm.repositories.MainRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel
{
	
	// Repository :
	private MainRepository mainRepository;
	
	// LiveData planète :
	private MutableLiveData<Planete> liveDataPlanete;
	
	// LiveData chargement :
	private MutableLiveData<Boolean> liveDataChargement;
	
	
	/**
	 * Initialisation.
	 * @param context Context
	 * @param mainRepository MainRepository
	 */
	public void init(Context context, MainRepository mainRepository)
	{
		// vérification que l'initialisation n'a pas déjà été faite :
		if (liveDataPlanete != null && liveDataChargement != null)
		{
			return;
		}
		
		// initialisation et premier chargement :
		this.mainRepository = mainRepository;
		liveDataPlanete = new MutableLiveData<>();
		liveDataChargement = new MutableLiveData<>();
		liveDataChargement.setValue(true);
		mainRepository.getLiveDataPlanete(context, liveDataPlanete, liveDataChargement);
	}
	
	/**
	 * Retourne le LiveData de la planète.
	 * @return LiveData
	 */
	public LiveData<Planete> getLiveDataPlanete()
	{
		return liveDataPlanete;
	}
	
	/**
	 * Retourne le LiveData du chargement.
	 * @return LiveData
	 */
	public LiveData<Boolean> getLiveDataChargement()
	{
		return liveDataChargement;
	}
	
	/**
	 * Récupération d'une nouvelle planète.
	 * @param context Context
	 */
	public void recupererPlanete(Context context)
	{
		liveDataChargement.setValue(true);
		mainRepository.getLiveDataPlanete(context, liveDataPlanete, liveDataChargement);
	}
	
	/**
	 * Mise à jour affichage selon la planète retournée, avec gestion des erreurs.
	 * @param context Context
	 * @param planete Planete
	 * @param textViewPlanete TextView planète
	 */
	public void actualiserPlanete(Context context, Planete planete, TextView textViewPlanete)
	{
		if (planete.erreur == null)
		{
			textViewPlanete.setText(context.getString(R.string.main_texte_planete, planete.name, planete.diameter, planete.climate, planete.gravity, planete.terrain));
		}
		else
		{
			Toast.makeText(context, planete.erreur, Toast.LENGTH_LONG).show();
		}
	}
	
}
