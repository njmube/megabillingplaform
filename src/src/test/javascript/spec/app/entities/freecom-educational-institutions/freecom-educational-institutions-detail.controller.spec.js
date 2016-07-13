'use strict';

describe('Controller Tests', function() {

    describe('Freecom_educational_institutions Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_educational_institutions, MockSchool_level, MockFree_cfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_educational_institutions = jasmine.createSpy('MockFreecom_educational_institutions');
            MockSchool_level = jasmine.createSpy('MockSchool_level');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_educational_institutions': MockFreecom_educational_institutions,
                'School_level': MockSchool_level,
                'Free_cfdi': MockFree_cfdi
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
