'use strict';

describe('Controller Tests', function() {

    describe('Freecom_educational_institutions Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_educational_institutions, MockFree_cfdi, MockC_school_level;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_educational_institutions = jasmine.createSpy('MockFreecom_educational_institutions');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            MockC_school_level = jasmine.createSpy('MockC_school_level');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_educational_institutions': MockFreecom_educational_institutions,
                'Free_cfdi': MockFree_cfdi,
                'C_school_level': MockC_school_level
            };
            createController = function() {
                $injector.get('$controller')("Freecom_educational_institutionsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_educational_institutionsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
