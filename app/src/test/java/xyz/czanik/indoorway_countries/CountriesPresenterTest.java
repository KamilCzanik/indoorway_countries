package xyz.czanik.indoorway_countries;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import xyz.czanik.indoorway_countries.countries.CountriesMVP;
import xyz.czanik.indoorway_countries.countries.CountriesPresenter;
import xyz.czanik.indoorway_countries.countries.CountryItem;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CountriesPresenterTest {

    private CountriesPresenter SUT;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) CountriesMVP.View view;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) CountriesMVP.Model model;

    @Captor ArgumentCaptor<OnLoadingCompleteListener> loadingListenerCaptor;
    @Captor ArgumentCaptor<String> stringCaptor;
    @Captor ArgumentCaptor<List<CountryItem>> countriesListCaptor;

    @Before
    public void setUp() {
        initMocks(this);
        SUT = new CountriesPresenter(view,model);
    }

    @Test
    public void whenPrepareViewThenDelegateModelToLoadData() {
        SUT.prepareView();

        verify(model,times(1)).loadCountries(loadingListenerCaptor.capture());
        assertEquals(SUT.getLoadingCompleteListener(),loadingListenerCaptor.getValue());
    }

    @Test
    public void whenRecyclerItemClickListenerOnItemClickThenStartSingleCountryActivity() {
        int pos = 2;
        String countryName = "Poland";
        when(view.getRecyclerAdapter().getCountries().get(pos)).thenReturn(new CountryItem(countryName,"url"));

        SUT.getRecyclerItemClickListener().onItemClick(pos);

        verify(view,times(1)).startSingleCountryActivityFor(stringCaptor.capture());
        assertEquals(countryName,stringCaptor.getValue());
    }

    @Test
    public void whenLoadingListenerOnFailureThenShowMessage() {
        String message = "message";
        SUT.getLoadingCompleteListener().onFailure(message);

        verify(view,times(1)).showMessage(stringCaptor.capture());
        assertEquals(message,stringCaptor.getValue());
    }
}
