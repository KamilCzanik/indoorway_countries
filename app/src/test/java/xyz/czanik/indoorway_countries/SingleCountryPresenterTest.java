package xyz.czanik.indoorway_countries;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import xyz.czanik.indoorway_countries.single_country.ComplexCountry;
import xyz.czanik.indoorway_countries.single_country.SingleCountryMVP;
import xyz.czanik.indoorway_countries.single_country.SingleCountryPresenter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class SingleCountryPresenterTest {

    private SingleCountryPresenter SUT;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) SingleCountryMVP.View view;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) SingleCountryMVP.Model model;

    @Captor ArgumentCaptor<String> stringCaptor;
    @Captor ArgumentCaptor<OnLoadingCompleteListener> listenerCaptor;
    @Captor ArgumentCaptor<ComplexCountry> countryCaptor;

    @Before
    public void setUp() {
        initMocks(this);
        SUT = new SingleCountryPresenter(view,model);
    }

    @Test
    public void whenPrepareViewThenDelegateModelToLoadData() {
        SUT.prepareView();

        verify(model,times(1)).loadCountryData(stringCaptor.capture(),listenerCaptor.capture());
        assertEquals(view.getCountryName(),stringCaptor.getValue());
        assertEquals(SUT.getLoadingListener(),listenerCaptor.getValue());
    }

    @Test
    public void whenLoadingListenerOnCompleteThenSetDataToView() {
        SUT.getLoadingListener().onComplete();

        verify(view,times(1)).setCountry(countryCaptor.capture());
        assertEquals(model.getCountry(),countryCaptor.getValue());
    }

    @Test
    public void whenLoadingListenerOnFailureThenShowErrorMessage() {
        String message = "message";

        SUT.getLoadingListener().onFailure(message);

        verify(view,times(1)).showMessage(stringCaptor.capture());
        assertEquals(message,stringCaptor.getValue());
    }
}
