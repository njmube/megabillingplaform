(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_info_customs_destructionDetailController', Freecom_info_customs_destructionDetailController);

    Freecom_info_customs_destructionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_info_customs_destruction', 'Freecom_destruction_certificate'];

    function Freecom_info_customs_destructionDetailController($scope, $rootScope, $stateParams, entity, Freecom_info_customs_destruction, Freecom_destruction_certificate) {
        var vm = this;
        vm.freecom_info_customs_destruction = entity;
        vm.load = function (id) {
            Freecom_info_customs_destruction.get({id: id}, function(result) {
                vm.freecom_info_customs_destruction = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_info_customs_destructionUpdate', function(event, result) {
            vm.freecom_info_customs_destruction = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
