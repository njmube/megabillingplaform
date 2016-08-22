'use strict';

describe('Controller Tests', function() {

    describe('Freecom_foreign_trade Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_foreign_trade, MockFree_cfdi, MockFreecom_incoterm, MockC_type_operation_ce, MockC_key_pediment, MockFreecom_addressee;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_foreign_trade = jasmine.createSpy('MockFreecom_foreign_trade');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            MockFreecom_incoterm = jasmine.createSpy('MockFreecom_incoterm');
            MockC_type_operation_ce = jasmine.createSpy('MockC_type_operation_ce');
            MockC_key_pediment = jasmine.createSpy('MockC_key_pediment');
            MockFreecom_addressee = jasmine.createSpy('MockFreecom_addressee');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_foreign_trade': MockFreecom_foreign_trade,
                'Free_cfdi': MockFree_cfdi,
                'Freecom_incoterm': MockFreecom_incoterm,
                'C_type_operation_ce': MockC_type_operation_ce,
                'C_key_pediment': MockC_key_pediment,
                'Freecom_addressee': MockFreecom_addressee
            };
            createController = function() {
                $injector.get('$controller')("Freecom_foreign_tradeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_foreign_tradeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
