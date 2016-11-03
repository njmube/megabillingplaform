'use strict';

describe('Controller Tests', function() {

    describe('Com_foreign_trade Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_foreign_trade, MockCfdi, MockCom_incoterm, MockC_type_operation_ce, MockC_key_pediment, MockCom_addressee;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_foreign_trade = jasmine.createSpy('MockCom_foreign_trade');
            MockCfdi = jasmine.createSpy('MockCfdi');
            MockCom_incoterm = jasmine.createSpy('MockCom_incoterm');
            MockC_type_operation_ce = jasmine.createSpy('MockC_type_operation_ce');
            MockC_key_pediment = jasmine.createSpy('MockC_key_pediment');
            MockCom_addressee = jasmine.createSpy('MockCom_addressee');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_foreign_trade': MockCom_foreign_trade,
                'Cfdi': MockCfdi,
                'Com_incoterm': MockCom_incoterm,
                'C_type_operation_ce': MockC_type_operation_ce,
                'C_key_pediment': MockC_key_pediment,
                'Com_addressee': MockCom_addressee
            };
            createController = function() {
                $injector.get('$controller')("Com_foreign_tradeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_foreign_tradeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
