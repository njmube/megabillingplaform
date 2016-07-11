'use strict';

describe('Controller Tests', function() {

    describe('Free_receiver Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFree_receiver, MockC_country, MockC_state, MockC_municipality, MockC_colony, MockC_zip_code, MockType_taxpayer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFree_receiver = jasmine.createSpy('MockFree_receiver');
            MockC_country = jasmine.createSpy('MockC_country');
            MockC_state = jasmine.createSpy('MockC_state');
            MockC_municipality = jasmine.createSpy('MockC_municipality');
            MockC_colony = jasmine.createSpy('MockC_colony');
            MockC_zip_code = jasmine.createSpy('MockC_zip_code');
            MockType_taxpayer = jasmine.createSpy('MockType_taxpayer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Free_receiver': MockFree_receiver,
                'C_country': MockC_country,
                'C_state': MockC_state,
                'C_municipality': MockC_municipality,
                'C_colony': MockC_colony,
                'C_zip_code': MockC_zip_code,
                'Type_taxpayer': MockType_taxpayer
            };
            createController = function() {
                $injector.get('$controller')("Free_receiverDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:free_receiverUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
