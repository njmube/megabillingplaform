'use strict';

describe('Controller Tests', function() {

    describe('Freecom_taxlegends Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_taxlegends, MockFree_cfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_taxlegends = jasmine.createSpy('MockFreecom_taxlegends');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_taxlegends': MockFreecom_taxlegends,
                'Free_cfdi': MockFree_cfdi
            };
            createController = function() {
                $injector.get('$controller')("Freecom_taxlegendsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_taxlegendsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
