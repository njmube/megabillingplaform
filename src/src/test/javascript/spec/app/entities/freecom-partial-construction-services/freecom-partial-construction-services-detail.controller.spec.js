'use strict';

describe('Controller Tests', function() {

    describe('Freecom_partial_construction_services Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_partial_construction_services, MockFree_cfdi, MockC_federal_entity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_partial_construction_services = jasmine.createSpy('MockFreecom_partial_construction_services');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            MockC_federal_entity = jasmine.createSpy('MockC_federal_entity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_partial_construction_services': MockFreecom_partial_construction_services,
                'Free_cfdi': MockFree_cfdi,
                'C_federal_entity': MockC_federal_entity
            };
            createController = function() {
                $injector.get('$controller')("Freecom_partial_construction_servicesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_partial_construction_servicesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
