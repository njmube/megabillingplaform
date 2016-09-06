'use strict';

describe('Controller Tests', function() {

    describe('Freecom_partial_construction_services Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_partial_construction_services, MockFree_cfdi, MockC_federal_entity, MockC_municipality, MockC_colony, MockC_zip_code;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_partial_construction_services = jasmine.createSpy('MockFreecom_partial_construction_services');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            MockC_federal_entity = jasmine.createSpy('MockC_federal_entity');
            MockC_municipality = jasmine.createSpy('MockC_municipality');
            MockC_colony = jasmine.createSpy('MockC_colony');
            MockC_zip_code = jasmine.createSpy('MockC_zip_code');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_partial_construction_services': MockFreecom_partial_construction_services,
                'Free_cfdi': MockFree_cfdi,
                'C_federal_entity': MockC_federal_entity,
                'C_municipality': MockC_municipality,
                'C_colony': MockC_colony,
                'C_zip_code': MockC_zip_code
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
