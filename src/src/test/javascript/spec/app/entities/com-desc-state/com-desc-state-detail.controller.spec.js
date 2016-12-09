'use strict';

describe('Controller Tests', function() {

    describe('Com_desc_state Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_desc_state, MockCom_public_notaries, MockType_state, MockC_colony, MockC_municipality, MockC_state, MockC_country, MockC_zip_code;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_desc_state = jasmine.createSpy('MockCom_desc_state');
            MockCom_public_notaries = jasmine.createSpy('MockCom_public_notaries');
            MockType_state = jasmine.createSpy('MockType_state');
            MockC_colony = jasmine.createSpy('MockC_colony');
            MockC_municipality = jasmine.createSpy('MockC_municipality');
            MockC_state = jasmine.createSpy('MockC_state');
            MockC_country = jasmine.createSpy('MockC_country');
            MockC_zip_code = jasmine.createSpy('MockC_zip_code');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_desc_state': MockCom_desc_state,
                'Com_public_notaries': MockCom_public_notaries,
                'Type_state': MockType_state,
                'C_colony': MockC_colony,
                'C_municipality': MockC_municipality,
                'C_state': MockC_state,
                'C_country': MockC_country,
                'C_zip_code': MockC_zip_code
            };
            createController = function() {
                $injector.get('$controller')("Com_desc_stateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_desc_stateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
