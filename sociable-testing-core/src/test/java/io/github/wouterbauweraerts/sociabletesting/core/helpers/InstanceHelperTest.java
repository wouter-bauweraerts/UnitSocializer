package io.github.wouterbauweraerts.sociabletesting.core.helpers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.wouterbauweraerts.sociabletesting.core.context.SociableTestContext;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterface;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterfaceImpl;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.SimpleDummy;
import io.github.wouterbauweraerts.sociabletesting.core.factory.MockFactory;
import io.github.wouterbauweraerts.sociabletesting.core.factory.TypeHelper;

@ExtendWith(MockitoExtension.class)
class InstanceHelperTest {
    private static final SociableTestContext CTX = SociableTestContext.getInstance();
    @InjectMocks
    InstanceHelper instanceHelper;
    @Mock
    MockFactory mockFactory;
    @Mock
    TypeResolver typeResolver;
    @Mock
    TypeHelper typeHelper;

    @BeforeEach
    void setUp() {
        CTX.clear();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockFactory, typeResolver, typeHelper);
    }

    @Test
    void instantiate_instanceOfTypeAlreadyExists_returnsExistingInstance() {
        Class<?> type = SimpleDummy.class;
        SimpleDummy sd = mock(SimpleDummy.class);
        CTX.putIfAbsent(type, sd);

        assertThat(instanceHelper.instantiate(type)).isSameAs(sd);
        verifyNoInteractions(mockFactory, typeResolver, typeHelper);
    }

    @Test
    void instantiate_instanceDoesNotExist_shouldBeMocked_returnsExpectedMock() {
        Class<?> type = SimpleDummy.class;
        SimpleDummy sd = mock(SimpleDummy.class);

        when(mockFactory.shouldMock(type)).thenReturn(true);
        doReturn(sd).when(mockFactory).mock(type);

        assertThat(instanceHelper.instantiate(type)).isSameAs(sd);
        verify(mockFactory).shouldMock(type);
        verify(mockFactory).mock(type);

        verifyNoInteractions(typeResolver, typeHelper);
    }

    @Test
    void instantiate_instanceDoesNotExist_shouldNotBeMocked_isAbstract_resolvesAndInstantiatesType_addsToMapTwice() {
        Class<?> abstractType = DummyInterface.class;
        Class<?> concreteType = DummyInterfaceImpl.class;
        Constructor<DummyInterface> defaultConstructor = mock(Constructor.class);
        DummyInterfaceImpl dummyInterface = mock(DummyInterfaceImpl.class);

        when(mockFactory.shouldMock(abstractType)).thenReturn(false);
        when(typeResolver.isAbstract(abstractType)).thenReturn(true);
        doReturn(concreteType).when(typeResolver).resolveType(abstractType);
        doReturn(defaultConstructor).when(typeHelper).getConstructor(concreteType);
        doReturn(dummyInterface).when(typeHelper).createInstance(eq(defaultConstructor), any(Supplier.class));

        assertThat(instanceHelper.instantiate(abstractType)).isSameAs(dummyInterface);
        assertThat(CTX.get(abstractType)).isSameAs(dummyInterface);
        assertThat(CTX.get(concreteType)).isSameAs(dummyInterface);

        verify(mockFactory).shouldMock(abstractType);
        verify(typeResolver).isAbstract(abstractType);
        verify(typeResolver).resolveType(abstractType);
        verify(typeHelper).getConstructor(concreteType);
        verify(typeHelper).createInstance(eq(defaultConstructor), any(Supplier.class));
    }

    @Test
    void instantiateConcreteType_doesNotYetExist_shouldNotBeMocked_instantiatesAndAddsToMap_andReturns() {
        Class<?> type = SimpleDummy.class;
        Constructor<SimpleDummy> defaultConstructor = mock(Constructor.class);
        SimpleDummy sd = mock(SimpleDummy.class);

        when(mockFactory.shouldMock(type)).thenReturn(false);
        when(typeResolver.isAbstract(type)).thenReturn(false);
        doReturn(defaultConstructor).when(typeHelper).getConstructor(type);
        doReturn(sd).when(typeHelper).createInstance(eq(defaultConstructor), any(Supplier.class));

        assertThat(instanceHelper.instantiate(type)).isSameAs(sd);
        assertThat(CTX.get(type)).isSameAs(sd);

        verify(mockFactory).shouldMock(type);
        verify(typeResolver).isAbstract(type);
        verify(typeHelper).getConstructor(type);
        verify(typeHelper).createInstance(eq(defaultConstructor), any(Supplier.class));
    }
}