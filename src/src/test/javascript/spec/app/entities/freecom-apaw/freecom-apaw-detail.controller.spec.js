'use strict';

describe('Controller Tests', function() {

    describe('Freecom_apaw Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_apaw, MockWell_type, MockAcquired_title, MockFeatures_work_piece, MockFree_cfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_apaw = jasmine.createSpy('MockFreecom_apaw');
            MockWell_type = jasmine.createSpy('MockWell_type');
            MockAcquired_title = jasmine.createSpy('MockAcquired_title');
            MockFeatures_work_piece = jasmine.createSpy('MockFeatures_work_piece');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_apaw': MockFreecom_apaw,
                'Well_type': MockWell_type,
                'Acquired_title': MockAcquired_title,
                'Features_work_piece': MockFeatures_work_piece,
                'Free_cfdi': MockFree_cfdi
            };
            createController = function() {
                $injector.get('$controller')("Freecom_apawDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_apawUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
