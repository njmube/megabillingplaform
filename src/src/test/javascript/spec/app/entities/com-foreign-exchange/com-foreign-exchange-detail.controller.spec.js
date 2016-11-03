'use strict';

describe('Controller Tests', function() {

    describe('Com_foreign_exchange Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_foreign_exchange, MockC_type_operation, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_foreign_exchange = jasmine.createSpy('MockCom_foreign_exchange');
            MockC_type_operation = jasmine.createSpy('MockC_type_operation');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_foreign_exchange': MockCom_foreign_exchange,
                'C_type_operation': MockC_type_operation,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_foreign_exchangeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_foreign_exchangeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
