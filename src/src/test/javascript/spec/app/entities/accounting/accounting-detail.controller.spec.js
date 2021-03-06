'use strict';

describe('Controller Tests', function() {

    describe('Accounting Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAccounting, MockFreecom_ine_entity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAccounting = jasmine.createSpy('MockAccounting');
            MockFreecom_ine_entity = jasmine.createSpy('MockFreecom_ine_entity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Accounting': MockAccounting,
                'Freecom_ine_entity': MockFreecom_ine_entity
            };
            createController = function() {
                $injector.get('$controller')("AccountingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:accountingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
