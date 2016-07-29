'use strict';

describe('Controller Tests', function() {

    describe('Freecom_foreign_exchange Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_foreign_exchange, MockC_type_operation, MockFree_cfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_foreign_exchange = jasmine.createSpy('MockFreecom_foreign_exchange');
            MockC_type_operation = jasmine.createSpy('MockC_type_operation');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_foreign_exchange': MockFreecom_foreign_exchange,
                'C_type_operation': MockC_type_operation,
                'Free_cfdi': MockFree_cfdi
            };
            createController = function() {
                $injector.get('$controller')("Freecom_foreign_exchangeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_foreign_exchangeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
