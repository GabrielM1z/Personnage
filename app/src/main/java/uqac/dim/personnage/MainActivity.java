package uqac.dim.personnage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Personnage mage;
    private Personnage guerrier;
    private Personnage personnageCourant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de l'interface
        mage = new Mage();
        guerrier = new Guerrier();
        personnageCourant = mage;
        chargerPersonnage();

        ((EditText)findViewById(R.id.editTxt_nom)).setEnabled(false);
        ((EditText)findViewById(R.id.editTxt_classe)).setEnabled(false);
        ((Switch)findViewById(R.id.switch_0)).setEnabled(false);
        ((EditText)findViewById(R.id.editTxt_pv)).setEnabled(false);
        ((EditText)findViewById(R.id.editTxt_ca)).setEnabled(false);
        ((EditText)findViewById(R.id.editTxt_dmg)).setEnabled(false);
        ((EditText)findViewById(R.id.editTxt_pdm)).setEnabled(false);

        if (personnageCourant.getAlignement() == Personnage.Alignement.BON){
            ((Switch)findViewById(R.id.switch_0)).setText(R.string.switch_txt_bon);
        }
        else {
            ((Switch)findViewById(R.id.switch_0)).setText(R.string.switch_txt_mauvais);
        }

        ((ImageView)findViewById(R.id.img)).setImageResource(R.drawable.mage);

        //// Initialisation des listener

        // radio bouton guerrier
        RadioButton radionBtnGuerrier = (RadioButton) findViewById(R.id.radiobtn_1);
        radionBtnGuerrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personnageCourant instanceof Mage){
                    changerEnGuerrier(v);
                }
            }
        });

        // radio bouton mage
        RadioButton radionBtnMage = (RadioButton) findViewById(R.id.radiobtn_2);
        radionBtnMage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personnageCourant instanceof Guerrier){
                    changerEnMage(v);
                }
            }
        });

        // bouton sauvegarder
        Button btnSauvegarder = (Button) findViewById(R.id.btn_1);
        btnSauvegarder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sauvegarderProfil(v);
            }
        });

        // bouton nouveau
        Button btnNouveau = (Button) findViewById(R.id.btn_2);
        btnNouveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nouveauProfil(v);
            }
        });

        // bouton réinitialiser
        Button btnReinisialiser = (Button) findViewById(R.id.btn_3);
        btnReinisialiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reinitialiserProfil(v);
            }
        });

        // checkbox modification des stats
        CheckBox checkBoxModifStats = (CheckBox) findViewById(R.id.ckbox);
        checkBoxModifStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean bool = ((CheckBox)findViewById(R.id.ckbox)).isChecked();
                ((EditText)findViewById(R.id.editTxt_nom)).setEnabled(bool);
                ((EditText)findViewById(R.id.editTxt_classe)).setEnabled(bool);
                ((Switch)findViewById(R.id.switch_0)).setEnabled(bool);
                ((EditText)findViewById(R.id.editTxt_pv)).setEnabled(bool);
                ((EditText)findViewById(R.id.editTxt_ca)).setEnabled(bool);
                ((EditText)findViewById(R.id.editTxt_dmg)).setEnabled(bool);
                ((EditText)findViewById(R.id.editTxt_pdm)).setEnabled(bool);
            }
        });

        // switch bon / mauvais
        Switch switchBonMauvais = (Switch) findViewById(R.id.switch_0);
        switchBonMauvais.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ((Switch)findViewById(R.id.switch_0)).setText(R.string.switch_txt_mauvais);
                }
                else {
                    ((Switch)findViewById(R.id.switch_0)).setText(R.string.switch_txt_bon);
                }
            }
        });

    }

    /*
        Dans cette méthode, je transfère les informations contenues dans le personnage courant
        vers les Widgets de main_activity
    */
    private void chargerPersonnage(){

        Log.i("DIM", "chargerProfil");

        ((EditText)findViewById(R.id.editTxt_nom)).setText(personnageCourant.getNom());
        ((EditText)findViewById(R.id.editTxt_classe)).setText(personnageCourant.getClasse());
        ((Switch)findViewById(R.id.switch_0)).setChecked(personnageCourant.getAlignement() == Personnage.Alignement.MAUVAIS);
        ((EditText)findViewById(R.id.editTxt_pv)).setText(String.valueOf(personnageCourant.getPV()));
        ((EditText)findViewById(R.id.editTxt_ca)).setText(String.valueOf(personnageCourant.getCA()));
        ((EditText)findViewById(R.id.editTxt_dmg)).setText(String.valueOf(personnageCourant.getDMG()));

        if (personnageCourant instanceof Mage){
            ((EditText)findViewById(R.id.editTxt_pdm)).setText(String.valueOf(((Mage) personnageCourant).getPM()));
        }
    }


    /*
        La méthode appelée par le bouton "réinitialiser"
        Je recrée l'object et je le recharge dans les widgets
    */

    public void reinitialiserProfil(View v){

        Log.i("DIM", "reinitialiserProfil");

        if (personnageCourant instanceof Mage){
            mage = new Mage();
            personnageCourant = mage;
        }
        else{
            guerrier = new Guerrier();
            personnageCourant = guerrier;
        }
        chargerPersonnage();
    }

    /*
        La méthode appelée par le bouton "nouveau"
        Les valeurs des widgets sont éffacé
    */

    public void nouveauProfil(View v){

        Log.i("DIM", "nouveauProfil");

        ((EditText)findViewById(R.id.editTxt_nom)).setText("");
        ((EditText)findViewById(R.id.editTxt_classe)).setText("");
        ((Switch)findViewById(R.id.switch_0)).setChecked(false);
        ((EditText)findViewById(R.id.editTxt_pv)).setText("");
        ((EditText)findViewById(R.id.editTxt_ca)).setText("");
        ((EditText)findViewById(R.id.editTxt_dmg)).setText("");

        if (personnageCourant instanceof Mage){
            ((EditText)findViewById(R.id.editTxt_pdm)).setText("");
        }
    }

    /*
        La méthode appelée par le bouton "sauvegarder"
        Je set l'objet aux valeurs présentes dans les widgets
    */

    public void sauvegarderProfil(View v){

        Log.i("DIM", "sauvegardeProfil");

        personnageCourant.setNom(((EditText)findViewById(R.id.editTxt_nom)).getText().toString());

        if ( ((Switch)findViewById(R.id.switch_0)).isChecked()){
            personnageCourant.setAlignement(Personnage.Alignement.MAUVAIS);
        }
        else {
            personnageCourant.setAlignement(Personnage.Alignement.BON);
        }

        personnageCourant.setPV(Integer.parseInt(((EditText)findViewById(R.id.editTxt_pv)).getText().toString()));
        personnageCourant.setCA(Integer.parseInt(((EditText)findViewById(R.id.editTxt_ca)).getText().toString()));
        personnageCourant.setDMG(Integer.parseInt(((EditText)findViewById(R.id.editTxt_dmg)).getText().toString()));

        if (personnageCourant instanceof Mage){

            ((Mage) personnageCourant).setPM(Integer.parseInt(((EditText)findViewById(R.id.editTxt_pdm)).getText().toString()));
        }
    }

    public void changerEnGuerrier(View v){

        Log.i("DIM", "changer en guerrier");

        ((TextView)findViewById(R.id.txt_pdm)).setVisibility(View.GONE);
        ((EditText)findViewById(R.id.editTxt_pdm)).setVisibility(View.GONE);
        ((ImageView)findViewById(R.id.img)).setImageResource(R.drawable.guerrier);
        personnageCourant = guerrier;
        chargerPersonnage();
    }

    public void changerEnMage(View v){

        Log.i("DIM", "changer en mage");

        ((TextView)findViewById(R.id.txt_pdm)).setVisibility(View.VISIBLE);
        ((EditText)findViewById(R.id.editTxt_pdm)).setVisibility(View.VISIBLE);
        ((ImageView)findViewById(R.id.img)).setImageResource(R.drawable.mage);
        personnageCourant = mage;
        chargerPersonnage();
    }
}