(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_pficDetailController', Freecom_pficDetailController);

    Freecom_pficDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_pfic', 'Free_cfdi'];

    function Freecom_pficDetailController($scope, $rootScope, $stateParams, entity, Freecom_pfic, Free_cfdi) {
        var vm = this;
        vm.freecom_pfic = entity;
        vm.load = function (id) {
            Freecom_pfic.get({id: id}, function(result) {
                vm.freecom_pfic = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_pficUpdate', function(event, result) {
            vm.freecom_pfic = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
