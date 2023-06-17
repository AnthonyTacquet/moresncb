package be.antwaan.moresncb.logica;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import be.antwaan.moresncb.R;
import be.antwaan.moresncb.global.NMBS.Composition;
import be.antwaan.moresncb.global.NMBS.Unit;

public class CompositionManager {
    private Composition composition;
    private List<Unit> units;

    public CompositionManager(Composition composition){
        this.composition = composition;
        this.units = composition.getSegments()
                .stream()
                .flatMap(segment -> segment.getUnits().stream())
                .collect(Collectors.toList());

        calculate();
    }

    private void calculate(){
        int tracNumber = 0;
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getTractionPosition() != tracNumber){
                tracNumber = units.get(i).getTractionPosition();

                if (i != 0)
                    setLastUnitImage(units.get(i - 1));

                setFirstUnitImage(units.get(i));

            } else{
                if (i == units.size() - 1)
                    setLastUnitImage(units.get(i));
                else
                    setUnitImage(units.get(i));
            }
        }
    }

    private void setLastUnitImage(Unit unit){
        if (unit.getSeatsFirstClass() > 0 && unit.getSeatsSecondClass() > 0)
            unit.setImageId(R.drawable.train_lastc21);
        else if (unit.getSeatsFirstClass() == 0 && unit.getSeatsSecondClass() > 0 && !unit.canPassToNextUnit())
            unit.setImageId(R.drawable.train_lastc2);
        else if (unit.getSeatsFirstClass() > 0 && unit.getSeatsSecondClass() == 0 && !unit.canPassToNextUnit())
            unit.setImageId(R.drawable.train_lastc1);
        else if (unit.getSeatsFirstClass() == 0 && unit.getSeatsSecondClass() > 0 && unit.canPassToNextUnit())
            unit.setImageId(R.drawable.train_double_lastc2);
        else if (unit.getSeatsFirstClass() > 0 && unit.getSeatsSecondClass() == 0 && unit.canPassToNextUnit())
            unit.setImageId(R.drawable.train_double_lastc1);
        else if (unit.getSeatsFirstClass() == 0 && unit.getSeatsSecondClass() == 0)
            unit.setImageId(R.drawable.train_loco_first);

    }

    private void setFirstUnitImage(Unit unit){
        if (unit.getSeatsFirstClass() > 0 && unit.getSeatsSecondClass() > 0)
            unit.setImageId(R.drawable.train_firstc12);
        else if (unit.getSeatsFirstClass() == 0 && unit.getSeatsSecondClass() > 0 && !unit.canPassToNextUnit())
            unit.setImageId(R.drawable.train_firstc2);
        else if (unit.getSeatsFirstClass() > 0 && unit.getSeatsSecondClass() == 0 && !unit.canPassToNextUnit())
            unit.setImageId(R.drawable.train_firstc1);
        else if (unit.getSeatsFirstClass() == 0 && unit.getSeatsSecondClass() > 0 && unit.canPassToNextUnit())
            unit.setImageId(R.drawable.train_double_firstc2);
        else if (unit.getSeatsFirstClass() > 0 && unit.getSeatsSecondClass() == 0 && unit.canPassToNextUnit())
            unit.setImageId(R.drawable.train_double_firstc1);

    }

    private void setUnitImage(Unit unit){
        if (unit.getSeatsFirstClass() > 0 && unit.getSeatsSecondClass() > 0)
            unit.setImageId(R.drawable.trainc21);
        else if (unit.getSeatsFirstClass() == 0 && unit.getSeatsSecondClass() > 0 && !unit.canPassToNextUnit())
            unit.setImageId(R.drawable.trainc2);
        else if (unit.getSeatsFirstClass() > 0 && unit.getSeatsSecondClass() == 0 && !unit.canPassToNextUnit())
            unit.setImageId(R.drawable.trainc1);
        else if (unit.getSeatsFirstClass() == 0 && unit.getSeatsSecondClass() > 0 && unit.canPassToNextUnit())
            unit.setImageId(R.drawable.train_double_c2);
        else if (unit.getSeatsFirstClass() > 0 && unit.getSeatsSecondClass() == 0 && unit.canPassToNextUnit())
            unit.setImageId(R.drawable.train_double_c1);
    }

    public List<Unit> getUnits(){
        return this.units;
    }

    public Unit getUnitById(int id){
        return units.get(id);
    }
}
