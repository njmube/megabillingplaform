'use strict';

describe('Controller Tests', function() {

    describe('Freecom_legend Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_legend, MockFreecom_taxlegends;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_legend = jasmine.createSpy('MockFreecom_legend');
            MockFreecom_taxlegends = jasmine.createSpy('MockFreecom_taxlegends');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_legend': MockFreecom_legend,
                'Freecom_taxlegends': MockFreecom_taxlegends
            };
            createController = function() {
                $injector.get('$controller')("Freecom_legendDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_legendUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
