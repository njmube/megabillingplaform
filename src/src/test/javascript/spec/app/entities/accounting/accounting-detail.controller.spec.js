'use strict';

describe('Controller Tests', function() {

    describe('Accounting Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAccounting, MockEntity_cfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAccounting = jasmine.createSpy('MockAccounting');
            MockEntity_cfdi = jasmine.createSpy('MockEntity_cfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Accounting': MockAccounting,
                'Entity_cfdi': MockEntity_cfdi
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
