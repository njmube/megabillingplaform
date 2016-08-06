'use strict';

describe('Controller Tests', function() {

    describe('Freecom_tax_type Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_tax_type;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_tax_type = jasmine.createSpy('MockFreecom_tax_type');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_tax_type': MockFreecom_tax_type
            };
            createController = function() {
                $injector.get('$controller')("Freecom_tax_typeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_tax_typeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
