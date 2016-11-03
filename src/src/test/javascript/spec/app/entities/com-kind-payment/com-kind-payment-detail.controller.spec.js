'use strict';

describe('Controller Tests', function() {

    describe('Com_kind_payment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_kind_payment, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_kind_payment = jasmine.createSpy('MockCom_kind_payment');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_kind_payment': MockCom_kind_payment,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_kind_paymentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_kind_paymentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
