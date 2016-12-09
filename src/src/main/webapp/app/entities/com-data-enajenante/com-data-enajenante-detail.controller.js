(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_data_enajenanteDetailController', Com_data_enajenanteDetailController);

    Com_data_enajenanteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_data_enajenante', 'Com_public_notaries'];

    function Com_data_enajenanteDetailController($scope, $rootScope, $stateParams, entity, Com_data_enajenante, Com_public_notaries) {
        var vm = this;
        vm.com_data_enajenante = entity;
        vm.load = function (id) {
            Com_data_enajenante.get({id: id}, function(result) {
                vm.com_data_enajenante = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_data_enajenanteUpdate', function(event, result) {
            vm.com_data_enajenante = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
