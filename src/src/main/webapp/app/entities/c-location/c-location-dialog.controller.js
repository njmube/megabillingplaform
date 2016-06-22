(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_locationDialogController', C_locationDialogController);

    C_locationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'C_location', 'C_municipality', 'C_zip_code', 'C_colony'];

    function C_locationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, C_location, C_municipality, C_zip_code, C_colony) {
        var vm = this;

        vm.c_location = entity;
        vm.clear = clear;
        vm.save = save;
        vm.c_municipalities = C_municipality.query();
        vm.c_zip_codes = C_zip_code.query({filter: 'c_location-is-null'});
        $q.all([vm.c_location.$promise, vm.c_zip_codes.$promise]).then(function() {
            if (!vm.c_location.c_zip_code || !vm.c_location.c_zip_code.id) {
                return $q.reject();
            }
            return C_zip_code.get({id : vm.c_location.c_zip_code.id}).$promise;
        }).then(function(c_zip_code) {
            vm.c_zip_codes.push(c_zip_code);
        });
        vm.c_colonies = C_colony.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.c_location.id !== null) {
                C_location.update(vm.c_location, onSaveSuccess, onSaveError);
            } else {
                C_location.save(vm.c_location, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:c_locationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
