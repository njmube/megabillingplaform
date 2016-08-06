(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_tax_typeDetailController', Freecom_tax_typeDetailController);

    Freecom_tax_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_tax_type'];

    function Freecom_tax_typeDetailController($scope, $rootScope, $stateParams, entity, Freecom_tax_type) {
        var vm = this;
        vm.freecom_tax_type = entity;
        vm.load = function (id) {
            Freecom_tax_type.get({id: id}, function(result) {
                vm.freecom_tax_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_tax_typeUpdate', function(event, result) {
            vm.freecom_tax_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
