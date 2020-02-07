package com.formationandroid.architecturemvvm.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.formationandroid.architecturemvvm.R;
import com.formationandroid.architecturemvvm.models.Planete;
import com.formationandroid.architecturemvvm.repositories.MainRepository;
import com.formationandroid.architecturemvvm.viewmodels.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity
{
	
	// TODO : * MVVM avec observe
	// TODO : * requète avec MVVM et retour
	// TODO : * test si rotation écran
	// TODO : * mise en forme texte
	// TODO : * gestion erreur
	// TODO : ~ cas 2 view models pour 1 activité
	// TODO : data binding + tests automatisés ?
	
	// Vues :
	private TextView textViewPlanete = null;
	private ProgressBar progressBarPlanete = null;
	
	// View model :
	private MainViewModel mainViewModel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// init :
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// vues :
		textViewPlanete = findViewById(R.id.informations_planete);
		progressBarPlanete = findViewById(R.id.progression_planete);
		
		// view model :
		mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
		mainViewModel.init(this, new MainRepository());
		mainViewModel.getLiveDataPlanete().observe(this, new PlaneteObserver());
		mainViewModel.getLiveDataChargement().observe(this, new ChargementObserver());
	}
	
	/**
	 * Listener clic bouton planète.
	 * @param view Bouton
	 */
	public void onClickBoutonPlanete(View view)
	{
		mainViewModel.recupererPlanete(this);
	}
	
	/**
	 * Observer planète.
	 */
	private class PlaneteObserver implements Observer<Planete>
	{
		@Override
		public void onChanged(Planete planete)
		{
			mainViewModel.actualiserPlanete(MainActivity.this, planete, textViewPlanete);
			
		}
	}
	
	/**
	 * Observer chargement.
	 */
	private class ChargementObserver implements Observer<Boolean>
	{
		@Override
		public void onChanged(Boolean chargement)
		{
			progressBarPlanete.setVisibility(chargement ? View.VISIBLE : View.GONE);
		}
	}
}
