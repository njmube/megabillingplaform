(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_educational_institutionsDetailController', Freecom_educational_institutionsDetailController);

    Freecom_educational_institutionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_educational_institutions', 'Free_cfdi', 'C_school_level'];

    function Freecom_educational_institutionsDetailController($scope, $rootScope, $stateParams, entity, Freecom_educational_institutions, Free_cfdi, C_school_level) {
        var vm = this;
        vm.freecom_educational_institutions = entity;
        vm.load = function (id) {
            Freecom_educational_institutions.get({id: id}, function(result) {
                vm.freecom_educational_institutions = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_educational_institutionsUpdate', function(event, result) {
            vm.freecom_educational_institutions = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
