(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_locationDialogController', C_locationDialogController);

    C_locationDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'C_location', 'C_municipality', 'C_zip_code'];

    function C_locationDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, C_location, C_municipality, C_zip_code) {
        var vm = this;
        vm.c_location = entity;
        vm.c_municipalitys = C_municipality.query();
        vm.c_zip_codes = C_zip_code.query({filter: 'c_location-is-null'});
        $q.all([vm.c_location.$promise, vm.c_zip_codes.$promise]).then(function() {
            if (!vm.c_location.c_zip_code || !vm.c_location.c_zip_code.id) {
                return $q.reject();
            }
            return C_zip_code.get({id : vm.c_location.c_zip_code.id}).$promise;
        }).then(function(c_zip_code) {
            vm.c_zip_codes.push(c_zip_code);
        });
        vm.load = function(id) {
            C_location.get({id : id}, function(result) {
                vm.c_location = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_locationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_location.id !== null) {
                C_location.update(vm.c_location, onSaveSuccess, onSaveError);
            } else {
                C_location.save(vm.c_location, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
